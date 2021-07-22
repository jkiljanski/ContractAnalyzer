import React, {useMemo, useState} from "react";
import ReactPaginate from "react-paginate";
import ReportViewer from "../reports/ReportViewer";
import classes from "../Styles.module.css";

const PER_PAGE = 10;

const Paginator = props => {

    const [currentPage, setCurrentPage] = useState(0);

    const offset = currentPage * PER_PAGE;

    const currentPageData = () => {
        props.reports
            .slice(offset, offset + PER_PAGE)
            .map(report =>
                <ReportViewer report={report}/>
            );
    }

    const pageCount = Math.ceil(props.reports.length / PER_PAGE);

    const handlePageClick = selectedPage => {
        setCurrentPage(selectedPage);
    }

    // let mCurrentPageData = useMemo(() => currentPageData(), [currentPage])

    return (
        <div>
            <ReactPaginate className={classes.brand}
            previousLabel={"← Previous"}
            nextLabel={"Next →"}
            pageCount={pageCount}
            onPageChange={handlePageClick}
            containerClassName={"pagination"}
            previousLinkClassName={"pagination__link"}
            nextLinkClassName={"pagination__link"}
            disabledClassName={"pagination__link--disabled"}
            activeClassName={"pagination__link--active"}
        />
            {currentPageData}
        </div>
    )
}

export default Paginator;