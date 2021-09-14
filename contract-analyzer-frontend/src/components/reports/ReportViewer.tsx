import React from 'react';
import {Card, CardBody} from "reactstrap";
import Report from "../../model/Report";

interface Props {
    style: string
    report: Report
}

const ReportViewer: React.FC<Props> = (props: Props) => {
    return (
        <tr>
            {Object.entries(props.report).map(([key, value]) => (
                <td>
                    <Card>
                        {/*WATCHOUT: can be botched after migration to TS, originally was: className={[classes.brandSmall,props.style]}*/}

                        <CardBody className={props.style}>
                            {value}
                        </CardBody>
                    </Card>
                </td>
            ))}
        </tr>
    );
}

export default ReportViewer;