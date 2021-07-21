import React, {useState, useRef} from "react";
import {Button, Form, InputGroup} from "reactstrap";
import { useKeycloak } from "@react-keycloak/web";
import ReportViewer from "../reports/ReportViewer";

const QueuesChecks = props => {

    const kafkaCheckToRun = props.kafkaCheckToRun;

    const incomingTopicInputRef = useRef();
    const outgoingTopicInputRef = useRef();
    const hostInputRef = useRef('kafka');
    const portInputRef = useRef('29092');

    const [kafkaCheckReport, setKafkaCheckReport] = useState('');

    const {keycloak} = useKeycloak();

    async function runKafkaCheck(incomingTopic, outgoingTopic, host, port) {

        if (!keycloak.authenticated) {
            alert("You're not logged in, please log in or check keycloak server")
        }

        let response = await fetch('/kafkaCheck/' + kafkaCheckToRun + '/run' +
            '?incomingTopic=' + incomingTopic + '&outgoingTopic=' + outgoingTopic +
            '&host=' + host + '&port=' + port, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + keycloak.token,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        });

        if (response.status !== 200)
            console.log("Oh!")
        else {
            const kafkaCheckReport = await response.json();
            setKafkaCheckReport(kafkaCheckReport);
        }
    }

    const formSubmitHandler = event => {
        event.preventDefault();

        const incomingTopic = incomingTopicInputRef.current.value;
        const outgoingTopic = outgoingTopicInputRef.current.value;
        const host = hostInputRef.current.value;
        const port = portInputRef.current.value;

        runKafkaCheck(incomingTopic, outgoingTopic, host, port).then(() => cleanForm());
    }

    const cleanForm = () => {
        incomingTopicInputRef.current.value = '';
        outgoingTopicInputRef.current.value = '';
        hostInputRef.current.value = '';
        portInputRef.current.value = '';
    }

    return (
        <>
        <Form onSubmit={formSubmitHandler}>
            <InputGroup>
                <label>Incoming topic</label>
                <input type="text"
                       name="incomingTopic"
                       placeholder="Please enter incoming topic"
                       ref={incomingTopicInputRef} />
            </InputGroup>
            <InputGroup>
                <label>Outgoing topic</label>
                <input type="text"
                        name="outgoingTopic"
                        placeholder="Please enter outgoing topic"
                        ref={outgoingTopicInputRef} />
            </InputGroup>
            <InputGroup>
                <label>Host</label>
                <input type="text"
                       name="host"
                       placeholder="Please enter host. The initial host is localhost:8080"
                       ref={hostInputRef}/>
            </InputGroup>
            <InputGroup>
                <label>Port</label>
                <input type="text"
                       name="port"
                       placeholder="Please enter port"
                        ref={portInputRef} />
            </InputGroup>
                    <Button type="submit">Run check</Button>
        </Form>
        <ReportViewer report={kafkaCheckReport} />
        </>
    )
}

export default QueuesChecks;