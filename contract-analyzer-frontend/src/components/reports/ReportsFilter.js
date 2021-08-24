import React, {useState, useRef} from "react";
import classes from "../Styles.module.css";
import {Button, Col, Form, Input, InputGroup, InputGroupAddon, Label, Row} from "reactstrap";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const ReportsFilter = props => {

    const resultInputRef = useRef();
    const reportBodyInputRef = useRef();
    const nameOfCheckInputRef = useRef();
    const userNameInputRef = useRef();

    const startTimeInputRef = useRef();
    const finishTimeInputRef = useRef();

    const [startDate, setStartDate] = useState(null);
    const [finishDate, setFinishDate] = useState(null);

    const formSubmitHandler = (event) => {
        event.preventDefault();

        const result = resultInputRef.current.value;
        const reportBody = reportBodyInputRef.current.value;
        const startTime = startTimeInputRef.current.value;
        const finishTime = finishTimeInputRef.current.value;
        const nameOfCheck = nameOfCheckInputRef.current.value;
        const userName = userNameInputRef.current.value;

        if ((startTime && !startDate) || (finishTime && !finishDate)) {
            return;
        }

        const startDateWithTime = convertToDateTimeFormat(startDate, startTime);
        const finishDateWithTime = convertToDateTimeFormat(finishDate, finishTime);

        console.log(result, reportBody, startDateWithTime, finishDateWithTime, nameOfCheck, userName)

        props.show(result, reportBody, startDateWithTime, finishDateWithTime, nameOfCheck, userName);
    }

    const convertToDateTimeFormat = (date, time) => {
        if (!date)
            return null;
        console.log(date + " XXXX " +time)
        return date.toISOString().slice(0,11) + time;
    }

    return (
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
                    <Label>From</Label>
                    <Row>
                        <DatePicker
                            selected={startDate}
                            onChange={(date) => setStartDate(date)} />
                            <Label>Time</Label>
                        <input
                            type="time"
                            name="startTime"
                            ref={startTimeInputRef}
                        />
                    </Row>
                </Col>
                <Col>
                    <Label>To</Label>
                    <Row>
                        <DatePicker
                            selected={finishDate}
                            onChange={(date) => setFinishDate(date)} />
                            <Label>Time</Label>
                            <input
                                type="time"
                                name="finishTime"
                                ref={finishTimeInputRef}
                            />
                    </Row>
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
                        <Label>Username</Label>
                        <input type="text"
                               name="userName"
                               ref={userNameInputRef} />
                    </InputGroup>
                </Col>
            </Row>
            <Button type="submit">Show reports</Button>
        </Form>
    )
}

export default ReportsFilter;