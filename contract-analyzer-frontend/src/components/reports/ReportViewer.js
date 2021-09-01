import React from 'react';
import {Card, CardBody} from "reactstrap";
import classes from "../Styles.module.css";

const ReportViewer = (props) => {
    return (
            <tr>
            {Object.entries(props.report).map( ([key, value]) => (
                <td>
                    <Card>
                        <CardBody className={[classes.brandSmall, props.style]}>
                            {value}
                        </CardBody>
                    </Card>
                </td>
            ))}
            </tr>
    );
}

export default ReportViewer;