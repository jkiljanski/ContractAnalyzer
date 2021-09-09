import React, {useRef, useState} from "react";
import classes from "../Styles.module.css";
import {Button, Col, Form, InputGroup, Label, Row} from "reactstrap";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import moment from "moment";

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


        console.log(result, reportBody, startDateWithTime, finishDateWithTime, nameOfCheck, userName + " COJESTKURWA")

        props.show(result, reportBody, startDateWithTime, finishDateWithTime, nameOfCheck, userName, 0);
    }

    const convertToDateTimeFormat = (date, time) => {

        if (date === null) {
            return ""
        }

        const formattedDate = moment(date).format("YYYY-MM-DD")
        return time.length === 0 ? formattedDate + "T00:00" : formattedDate + 'T' + time;
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

                            dateFormat={"yyyy-MM-dd"}
                            selected={startDate}
                            onChange={(date) => setStartDate(date)}/>
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
                            dateFormat={"yyyy-MM-dd"}
                            selected={finishDate}
                            onChange={(date) => setFinishDate(date)}/>
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
                               ref={nameOfCheckInputRef}/>
                    </InputGroup>
                </Col>
            </Row>
            <Row>
                <Col>
                    <InputGroup>
                        <Label>Report body</Label>
                        <input type="text"
                               name="reportBody"
                               ref={reportBodyInputRef}/>
                    </InputGroup>
                </Col>
            </Row>
            <Row>
                <Col>
                    <InputGroup>
                        <Label>Username</Label>
                        <input type="text"
                               name="userName"
                               ref={userNameInputRef}/>
                    </InputGroup>
                </Col>
            </Row>
            <Button type="submit">Show reports</Button>
        </Form>
    )
}

export default ReportsFilter;