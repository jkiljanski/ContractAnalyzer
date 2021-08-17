import React, {useRef} from "react";
import classes from "../Styles.module.css";
import {Button, Col, Form, Input, InputGroup, InputGroupAddon, Label, Row} from "reactstrap";

const ReportsFilter = props => {

    const resultInputRef = useRef();
    const reportBodyInputRef = useRef();
    const timestampInputRef = useRef();
    const nameOfCheckInputRef = useRef();
    const userNameInputRef = useRef();

    const formSubmitHandler = (event) => {
        event.preventDefault();

        const result = resultInputRef.current.value;
        const reportBody = reportBodyInputRef.current.value;
        const timestamp = timestampInputRef.current.value;
        const nameOfCheck = nameOfCheckInputRef.current.value;
        const userName = userNameInputRef.current.value;

        console.log(result, reportBody, timestamp, nameOfCheck, userName)

        props.show(result, reportBody, timestamp, nameOfCheck, userName);

    }

    return (
        <>
            <Form className={classes.brand} onSubmit={formSubmitHandler}>
                <Row>
                    <Col>
                        <InputGroup>
                            <Label>Result</Label>
                            <select name="result"
                                    ref={resultInputRef}>
                                <option value="">-</option>
                                <option value="PASSED">Passed</option>
                                <option value="FAILED">Failed</option>
                            </select>
                        </InputGroup>
                    </Col>
                    <Col>
                        <InputGroup>
                            <Label>Name of check</Label>
                            <input type="text"
                                   name="nameOfCheck"
                                   ref={nameOfCheckInputRef} />
                        </InputGroup>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <InputGroup>
                            <Label>Report body</Label>
                            <input type="text"
                                   name="reportBody"
                                   ref={reportBodyInputRef} />
                        </InputGroup>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <InputGroup>
                            <Label>Timestamp</Label>
                            <input type="text"
                                   name="timestamp"
                                   ref={timestampInputRef} />
                        </InputGroup>
                    </Col>
                    <Col>
                        <InputGroup>
                            <Label>Username</Label>
                            <input type="text"
                                   name="userName"
                                   ref={userNameInputRef} />
                        </InputGroup>
                    </Col>
                </Row>
                <Button type="submit">Show reports</Button>
            </Form>
        </>
    )
}

export default ReportsFilter;