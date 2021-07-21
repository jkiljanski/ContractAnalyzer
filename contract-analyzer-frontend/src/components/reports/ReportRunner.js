import React, {useState} from "react";
import ReactPaginate from 'react-paginate';
import {Button, Input, InputGroup, InputGroupAddon, ListGroup} from "reactstrap";
import classes from "../Styles.module.css";
import ReportViewer from "./ReportViewer";
import '../pagination/Paginator.css'
import {useKeycloak} from "@react-keycloak/web";


const ReportRunner = props => {


    const [reports, setReports] = useState([]);

    const [reportId, setReportId] = useState('');

    const [reportById, setReportById] = useState('');

    const [isError, setIsError] = useState(false);

    const [currentPage, setCurrentPage] = useState(0);

    const PER_PAGE = 10;
    const offset = currentPage * PER_PAGE;
    const currentPageData = reports
        .slice(offset, offset + PER_PAGE)
        .map(report =>
            <ReportViewer report={report}/>
        );
    const pageCount = Math.ceil(reports.length / PER_PAGE);

    const {keycloak} = useKeycloak();

    async function showAllReports() {
        setReportById('')
        let response = await fetch('/reports', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + keycloak.token,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        })

        const allReports = await response.json();

        setReports(Object.values(allReports))
        console.log(reports)
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

    const userInputHandler = event => {
        setReportId(event.target.value);
    }

    function handlePageClick({selected: selectedPage}) {
        setCurrentPage(selectedPage);
    }

    return (
        <div>
            <div className={classes.brand}>
                <Button className={classes.button} onClick={showAllReports}>Show all reports</Button></div>
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
            <ListGroup className={classes.reporPassed}>
                {currentPageData}
                {!isError && <ReportViewer report={reportById}/>}
                {isError && <div>{reportById}</div>}
            </ListGroup>
            {/*<div><Paginator reports={reports} /></div>*/}


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
            /></div>
        </div>);
};

export default ReportRunner;