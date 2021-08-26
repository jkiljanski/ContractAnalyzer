import React, {useState} from "react";
import ReactPaginate from 'react-paginate';
import {Button, Input, InputGroup, InputGroupAddon, Table} from "reactstrap";
import classes from "../Styles.module.css";
import ReportViewer from "./ReportViewer";
import '../pagination/Paginator.css'
import {useKeycloak} from "@react-keycloak/web";
import ReportsFilter from "./ReportsFilter";
import ReportTableHeaders from "./ReportTableHeaders";


const ReportRunner = props => {


    const [reports, setReports] = useState([]);

    const [reportId, setReportId] = useState('');

    const [reportById, setReportById] = useState('');

    const [isError, setIsError] = useState(false);


    const [pageCount, setPageCount] = useState()

    const [queryArgs, setQueryArgs] = useState([""])


    const reportDependingOnResult = (report) => {

        return report.result === 'PASSED' ?
            <ReportViewer key={report.id} style={classes.reportPassed} report={report}/> :
            <ReportViewer key={report.id} style={classes.reportFailed} report={report}/>
    }

    const {keycloak} = useKeycloak();

    async function fetchFilteredReports(result, reportBody, startDateWithTime, finishDateWithTime, nameOfCheck, userName, pageNumber) {

        setReportById('')

        setQueryArgs([result, reportBody, startDateWithTime, finishDateWithTime, nameOfCheck, userName]);

        console.log(pageNumber + " INSIDE FETCH FUNCTION")

        let response = await fetch('/filteredReports?result=' + result + '&reportBody=' + reportBody +
            '&timestampFrom=' + startDateWithTime + '&timestampTo=' + finishDateWithTime + '&nameOfCheck=' + nameOfCheck + '&userName=' + userName + '&pageNumber=' + pageNumber, {
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
            let readyReports = allReports.content
            setReports(readyReports.map(report => reportDependingOnResult(report)))
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
            setReportById(response.statusText)
            setIsError(true);
        } else {
            setIsError(false);
            const reportById = await response.json();
            setReportById(reportById);
        }
    }

    const userInputHandler = event => {
        setReportId(event.target.value);
    }

    const handlePageChange = async (selectedObject) => {



        await fetchFilteredReports(...queryArgs, selectedObject.selected);

    };


    return (
        <>
            <ReportsFilter show={fetchFilteredReports}/>
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
            /></div>}
        </>)
}

export default ReportRunner;