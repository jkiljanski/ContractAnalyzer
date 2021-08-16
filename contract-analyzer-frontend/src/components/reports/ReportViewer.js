import React from 'react';
import {Card, CardBody} from "reactstrap";
import classes from "../Styles.module.css";

const ReportViewer = (props) => {

    // const ob = {
    //     id: props.report.id,
    //     result: props.report.result,
    //     reportBody: props.report.reportBody,
    //     timestamp: props.report.timestamp,
    //     nameOfCheck: props.report.nameOfCheck,
    //     username: props.report.username
    // }

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
         </tr>)
}

export default ReportViewer;