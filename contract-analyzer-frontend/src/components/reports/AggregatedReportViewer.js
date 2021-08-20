import {Card, CardBody} from "reactstrap";
import classes from "../Styles.module.css";
import React from "react";

const AggregatedReportViewer = props => {
    return (
        <tr>
            <td>
                <Card>
                    <CardBody className={[classes.brandSmall, props.style]}>
                        {props.report.id}
                    </CardBody>
                </Card>
            </td>
            <td>
                <Card>
                    <CardBody className={[classes.brandSmall, props.style]}>
                        {props.report.aggregatedReportName}
                    </CardBody>
                </Card>
            </td>
            <td>
                {props.report.namesOfChecks.map(nameOfCheck => (
                    <Card>
                        <CardBody className={[classes.brandSmall, props.style]}>
                                {nameOfCheck}
                        </CardBody>
                    </Card>))}
            </td>
            <td>
                <Card>
                    <CardBody className={[classes.brandSmall, props.style]}>
                        {props.report.timestamp}
                    </CardBody>
                </Card>
            </td>

            <td>
                <tr>
                {props.report.failedTests.map( failedTest => (
                <Card>
                    <CardBody className={[classes.brandSmall, props.style]}>
                        <td>
                            {failedTest.id}
                        </td>
                        <td>
                            {failedTest.nameOfCheck}
                        </td>
                        <td>
                            {failedTest.reportBody}
                        </td>
                    </CardBody>
                </Card>))}
                </tr>
            </td>

            <td>
                <Card>
                    <CardBody className={[classes.brandSmall, props.style]}>
                        {props.report.passedPercentage}
                    </CardBody>
                </Card>
            </td>
            <td>
                <Card>
                    <CardBody className={[classes.brandSmall, props.style]}>
                        {props.report.failedPercentage}
                    </CardBody>
                </Card>
            </td>
            <td>
                <Card>
                    <CardBody className={[classes.brandSmall, props.style]}>
                        {props.report.userName}
                    </CardBody>
                </Card>
            </td>
        </tr>
    );
}

export default AggregatedReportViewer;