import React, {useRef, useState} from "react";
import {Button, Form, InputGroup} from "reactstrap";
import {useKeycloak} from "@react-keycloak/web";
import ReportViewer from "../reports/ReportViewer";
import classes from './QueuesChecks.module.css';
import Report from "../../model/Report";
import {API_BASE_URL} from "../../index";


interface Props {
    checkToRun: string
}

const KafkaCheckRunner: React.FC<Props> = (props:Props) => {


    const kafkaCheckToRun = props.checkToRun;

    const errorMessage = "Error! Invalid input or a problem on the server side";

    const incomingTopicInputRef = useRef<HTMLInputElement>(null);
    const outgoingTopicInputRef = useRef<HTMLInputElement>(null);
    const hostInputRef = useRef<HTMLInputElement>(null);
    const portInputRef = useRef<HTMLInputElement>(null);

    const [kafkaCheckReport, setKafkaCheckReport] = useState<Report | null>();

    const {keycloak} = useKeycloak();
    const [error, setError] = useState('');

    const [sending, setSending] = useState('');


    async function runKafkaCheck(incomingTopic: string, outgoingTopic: string, host: string, port: string) {
        setError('');

        if (!keycloak.authenticated) {
            alert("You're not logged in, please log in or check keycloak server")
        }


        setSending('Check is running, wait...')
        let response = await fetch(API_BASE_URL + '/kafkaCheck/' + kafkaCheckToRun + '/run' +
            '?incomingTopic=' + incomingTopic + '&outgoingTopic=' + outgoingTopic +
            '&host=' + host + '&port=' + port, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + keycloak.token,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        });
        setSending('');

        if (response.status !== 200) {
            console.log(response);
            setError(errorMessage);
        } else {
            const kafkaCheckReport = await response.json();
            setKafkaCheckReport(kafkaCheckReport);
        }
    }

    const formSubmitHandler = (event: { preventDefault: () => void; }) => {
        event.preventDefault();

        //must add form validation: possibly here (! to be thrown out)

        const incomingTopic = incomingTopicInputRef.current!.value;
        const outgoingTopic = outgoingTopicInputRef.current!.value;
        const host = hostInputRef.current!.value;
        const port = portInputRef.current!.value;

        runKafkaCheck(incomingTopic, outgoingTopic, host, port).then(() => cleanForm());
    }

    const cleanForm = () => {
        incomingTopicInputRef.current!.value = '';
        outgoingTopicInputRef.current!.value = '';
        hostInputRef.current!.value = '';
        portInputRef.current!.value = '';
    }

    return (
        <>
            <Form className={classes.form} onSubmit={formSubmitHandler}>
                <InputGroup>
                    <label className={classes.label}>Incoming topic</label>
                    <input className={classes.input}
                           type="text"
                           name="incomingTopic"
                           placeholder="Please enter incoming topic"
                           ref={incomingTopicInputRef}/>
                </InputGroup>
                <InputGroup>
                    <label className={classes.label}>Outgoing topic</label>
                    <input className={classes.input}
                           type="text"
                           name="outgoingTopic"
                           placeholder="Please enter outgoing topic"
                           ref={outgoingTopicInputRef}/>
                </InputGroup>
                <InputGroup>
                    <label className={classes.label}>Host</label>
                    <input className={classes.input}
                           type="text"
                           name="host"
                           placeholder="Please enter host. The initial host is localhost:8080"
                           ref={hostInputRef}/>
                </InputGroup>
                <InputGroup>
                    <label className={classes.label}>Port</label>
                    <input className={classes.input}
                           type="number"
                           name="port"
                           placeholder="Please enter port"
                           ref={portInputRef}/>
                </InputGroup>
                <Button type="submit">Run check</Button>
            </Form>
            {sending}
            {kafkaCheckReport?.result &&(kafkaCheckReport!.result === 'PASSED'  ?
                <ReportViewer style={classes.reportPassed} report={kafkaCheckReport as Report}/>  :
                <ReportViewer style={classes.reportFailed} report={kafkaCheckReport as Report}/>) }
            {error.length === 0 ? null : error}
        </>
    )
}

export default KafkaCheckRunner;