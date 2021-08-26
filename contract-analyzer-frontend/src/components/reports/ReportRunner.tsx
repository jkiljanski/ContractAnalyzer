import React, {useState} from "react";
import ReactPaginate from 'react-paginate';
import {Button, Input, InputGroup, InputGroupAddon, Table} from "reactstrap";
import classes from "../Styles.module.css";
import ReportViewer from "./ReportViewer";
import {useKeycloak} from "@react-keycloak/web";
import ReportsFilterForm from "./ReportsFilterForm";
import ReportTableHeaders from "./ReportTableHeaders";
import Report from "../../model/Report";
import {log} from "util";


interface Props {

    reportsToFetch: Report[]
    reportsHandler: (reports: React.SetStateAction<Report[]>) => void
}

const ReportRunner :React.FC<Props> = (props: Props) => {


    const [reports, setReports] = useState([]);

    const [reportId, setReportId] = useState('');

    const [reportById, setReportById] = useState<Report | undefined>(undefined);

    const [isError, setIsError] = useState(false);

    const [pageCount, setPageCount] = useState<number>(0)

    const [queryArgs, setQueryArgs] = useState(new Array<string>())


    const reportDependingOnResult = (report: Report | undefined) => {

        return report!.result === 'PASSED' ?
            <ReportViewer key={report!.id} style={classes.reportPassed} report={report as Report}/> :
            <ReportViewer key={report!.id} style={classes.reportFailed} report={report as Report}/>
    }

    const {keycloak} = useKeycloak();

    // async function fetchFilteredReports(result: string, reportBody: string, startDateWithTime: string, finishDateWithTime: string, nameOfCheck: string, userName: string, pageNumber: number) {
    async function fetchFilteredReports(pageNumber: number, args: Array<string>) {

        setReportById(undefined)
        setQueryArgs([...args]);



        let response = await fetch('/filteredReports?result=' + args[0] + '&reportBody=' + args[1] +
            '&timestampFrom=' + args[2] + '&timestampTo=' + args[3] + '&nameOfCheck=' + args[4] + '&userName=' + args[5] + '&pageNumber=' + pageNumber, {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + keycloak.token,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        })

        if (response.status !== 200)
            setIsError(true);
        else {
            setIsError(false);
            let allReports = await response.json();
            setPageCount(allReports.totalPages);
            console.log(allReports.totalPages)
            let readyReports = allReports.content
            console.log(readyReports)
            let mappedReport = readyReports.map((report: Report) => reportDependingOnResult(report));
            console.log(mappedReport)
            setReports(mappedReport)
        }
    }

    async function fetchReportById() {
        console.log(reportById)
        setReports([]);
        let response = await fetch('/reports/' + reportId, {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + keycloak.token,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        });

        if (response.status !== 200) {
            setIsError(true);
        } else {
            setIsError(false);
            const reportById = await response.json();
            setReportById(reportById);
        }
    }

    const userInputHandler = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setReportId(event.target.value);
    }


    const handlePageChange = async (selectedObject: { selected: number; }) => {


        await fetchFilteredReports(selectedObject.selected, queryArgs);

    };

    return (
        <>
            <ReportsFilterForm show={fetchFilteredReports}/>
            <InputGroup>
                <InputGroupAddon addonType="prepend"><Button className={classes.button} onClick={fetchReportById}>Show
                    report by id</Button>
                </InputGroupAddon>
                <Input type="text"
                       placeholder="Please enter id"
                       value={reportId}
                       onChange={userInputHandler}
                />
            </InputGroup>
            {isError}
            {(reports.length > 0 || reportById) && !isError &&
            <Table bordered={true}>
                <ReportTableHeaders/>
                <tbody>
                {reports}
                {reportDependingOnResult(reportById)}
                </tbody>
            </Table>}
            {isError && <div className={classes.reportFailed}>{reportById}</div>}

            {<div className={classes.brand}><ReactPaginate
                previousLabel={"←"}
                nextLabel={"→"}
                breakLabel={'...'}
                pageCount={pageCount}
                onPageChange={handlePageChange}
                previousLinkClassName={"pagination__link"}
                nextLinkClassName={"pagination__link"}
                disabledClassName={"pagination__link--disabled"}
                activeClassName={"pagination__link--active"}
                containerClassName={'pagination'}
                marginPagesDisplayed={3}
                pageRangeDisplayed={3}/></div>}
        </>)
}

export default ReportRunner;