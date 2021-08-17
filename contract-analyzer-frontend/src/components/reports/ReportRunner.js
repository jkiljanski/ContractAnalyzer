import React, {useState} from "react";
import ReactPaginate from 'react-paginate';
import {Button, Input, InputGroup, InputGroupAddon, Table} from "reactstrap";
import classes from "../Styles.module.css";
import ReportViewer from "./ReportViewer";
import '../pagination/Paginator.css'
import {useKeycloak} from "@react-keycloak/web";
import BootstrapTable from 'react-bootstrap-table-next';
import TableHeaderColumn from 'react-bootstrap-table';
import ReportsFilter from "./ReportsFilter";
import ReportTableHeaders from "./ReportTableHeaders";


const ReportRunner = () => {

    const [reports, setReports] = useState([]);

    const [reportId, setReportId] = useState('');

    const [reportById, setReportById] = useState('');

    const [isError, setIsError] = useState(false);

    const [currentPage, setCurrentPage] = useState(0);

    const reportDependingOnResult = (report) => {
        return report.result === 'PASSED' ?
            <ReportViewer key={report.id} style={classes.reportPassed} report={report}/> :
            <ReportViewer key={report.id} style={classes.reportFailed} report={report}/>
    }

    const PER_PAGE = 10;
    const offset = currentPage * PER_PAGE;

    const pageCount = Math.ceil(reports.length / PER_PAGE);

    const {keycloak} = useKeycloak();

    async function showFilteredReports(result, reportBody, timestamp, nameOfCheck, userName) {

        setReportById('')
        let response = await fetch('/filteredReports?result=' + result + '&reportBody=' + reportBody +
            '&timestamp=' + timestamp + '&nameOfCheck=' + nameOfCheck + '&userName=' + userName, {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + keycloak.token,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        })

        // setReportById('')
        // let response = await fetch('/reports', {
        //     method: 'GET',
        //     headers: {
        //         'Authorization': 'Bearer ' + keycloak.token,
        //         'Accept': 'application/json',
        //         'Content-Type': 'application/json'
        //     },
        // })

        if (response.status !== 200)
            setIsError(true);
        else {
            setIsError(false);
            const allReports = await response.json();
            setReports(allReports)
        }
    }

    async function getReportById() {
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

        console.log(response.statusText)

        if (response.status !== 200) {
            setReportById(response.statusText)
            setIsError(true);
        } else {
            setIsError(false);
            const reportById = await response.json();
            setReportById(reportById);
        }
    }

    const currentPageData = reports
        .slice(offset, offset + PER_PAGE)
        .map(report => reportDependingOnResult(report));

    const userInputHandler = event => {
        setReportId(event.target.value);
    }

    function handlePageClick({selected: selectedPage}) {
        setCurrentPage(selectedPage);
    }

    const data = {
        columns: [
            {
                dataField: 'id',
                text: 'ID',
                sort: true,
            },
            {
                dataField: 'result',
                text: 'Result',
                sort: true
            },
            {
                dataField: 'reportBody',
                text: 'Report body',
                sort: true
            },
            {
                dataField: 'timestamp',
                text: 'Timestamp',
                sort: true
            },
            {
                dataField: 'nameOfCheck',
                text: 'Name of check',
                sort: true
            },
            {
                dataField: 'username',
                text: 'Username',
                sort: true
            },
        ]
    }

    return (
        <>
            <ReportsFilter show={showFilteredReports} />
            <InputGroup>
                <InputGroupAddon addonType="prepend"><Button className={classes.button} onClick={getReportById}>Show
                    report by id</Button>
                </InputGroupAddon>
                <Input type="text"
                       placeholder="Please enter id"
                       value={reportId}
                       onChange={userInputHandler}
                />
            </InputGroup>
            {/*<ListGroup>*/}
            {isError}
            {(reports.length > 0 || reportById) && !isError &&
            <BootstrapTable bordered
                bootstrap4
                data={currentPageData}
                keyField='id'
                columns={data.columns}>
            </BootstrapTable>}
            {/*<Table bordered={true}>*/}
            {/*     <ReportTableHeaders />*/}
            {/*     <tbody>*/}
            {/*        {currentPageData}*/}
            {/*         {reportDependingOnResult(reportById)}*/}
            {/*     </tbody>*/}
            {/* </Table>}*/}

            {isError && <div className={classes.reportFailed}>{reportById}</div>}
            {/*</ListGroup>*/}
            {/*<div><Paginator reports={reports} /></div>*/}

            {reports.length > PER_PAGE &&
            <div className={classes.brand}><ReactPaginate
                previousLabel={"←"}
                nextLabel={"→"}
                breakLabel={'...'}
                pageCount={pageCount}
                onPageChange={handlePageClick}
                previousLinkClassName={"pagination__link"}
                nextLinkClassName={"pagination__link"}
                disabledClassName={"pagination__link--disabled"}
                activeClassName={"pagination__link--active"}
                containerClassName={'pagination'}
            /></div>}
        </>);
};

export default ReportRunner;