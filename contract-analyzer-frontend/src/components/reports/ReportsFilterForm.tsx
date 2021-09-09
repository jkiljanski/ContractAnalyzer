import React, {useRef, useState} from "react";
import classes from "../Styles.module.css";
import {Button, Col, Form, InputGroup, Label, Row} from "reactstrap";
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import moment from "moment";

const ReportsFilterForm = (props: { show: (pageNum: number, args: string[]) => Promise<void>; }) => {

    const resultInputRef = useRef<HTMLSelectElement>(null);
    const reportBodyInputRef = useRef<HTMLInputElement>(null);
    const nameOfCheckInputRef = useRef<HTMLInputElement>(null);
    const userNameInputRef = useRef<HTMLInputElement>(null);
    const startTimeInputRef = useRef<HTMLInputElement>(null);
    const finishTimeInputRef = useRef<HTMLInputElement>(null);

    const [startDate, setStartDate] = useState<Date | null>(null);
    const [finishDate, setFinishDate] = useState<Date | null>(null);

    const formSubmitHandler = (event: { preventDefault: () => void; }) => {
        event.preventDefault();


        const result = resultInputRef.current!.value;
        const reportBody = reportBodyInputRef.current!.value;
        const startTime = startTimeInputRef.current!.value;
        const finishTime = finishTimeInputRef.current!.value;
        const nameOfCheck = nameOfCheckInputRef.current!.value;
        const userName = userNameInputRef.current!.value;

        if ((startTime && !startDate) || (finishTime && !finishDate)) {
            return;
        }

        const startDateWithTime = convertToDateTimeFormat(startDate, startTime);
        const finishDateWithTime = convertToDateTimeFormat(finishDate, finishTime);

        console.log(startDateWithTime, finishDateWithTime, " dates MOTHERFUCKER")

        props.show(0,[result,reportBody,startDateWithTime,finishDateWithTime,nameOfCheck,userName]);
    }

    const convertToDateTimeFormat = (date: Date | null, time: string | null) => {

        if (date===null) {
            return ""
        }


        const formattedDate = moment(date).format("YYYY-MM-DD")

        return time ? formattedDate :

            formattedDate + 'T' + time;
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
                            selected={startDate ? moment(startDate).toDate() : null}
                            // onChange={(date) => setStartDate(date)}/>
                            onChange={ startDate => setStartDate(startDate as Date)}/>

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
                            selected={finishDate ? moment(finishDate).toDate() : null}
                            onChange={(date) => setFinishDate(date as Date)}/>
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

export default ReportsFilterForm;