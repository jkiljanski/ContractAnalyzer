import React from "react";

const AggregatedReportTableHeaders = () => {
    return (
    <>
        <thead>
        <tr>
            <th>Id</th>
            <th>Aggregated report name</th>
            <th>Names of checks</th>
            <th>Timestamp</th>
            <th>Failed checks ???</th>
            <th>Passed percentage</th>
            <th>Failed percentage</th>
            <th>Username</th>
        </tr>
        </thead>
    </>)
}

export default AggregatedReportTableHeaders;