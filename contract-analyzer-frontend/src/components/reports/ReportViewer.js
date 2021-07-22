import React from 'react';
import {Card, CardBody} from "reactstrap";
import classes from "../Styles.module.css";

const ReportViewer = (props) => {
    return (
        <div key={props.report.id}>
            <Card>
                <CardBody className={classes.brandSmall}>
                    {Object.entries(props.report).map( ([key, val]) =>
                        <div >{key}: {val}</div>
                    )}
                </CardBody>
            </Card>
        </div>
    );
}

export default ReportViewer;