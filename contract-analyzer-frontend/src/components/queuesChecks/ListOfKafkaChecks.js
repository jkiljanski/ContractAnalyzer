import React, {useState} from "react";
import {Button, ListGroup} from "reactstrap";
import classes from "../Styles.module.css";

const ListOfKafkaChecks = props => {

    const [selectedCheck, setSelectedCheck] = useState('');

    const onKafkaCheckClick = selected => {
        setSelectedCheck(selected);
        props.kafkaChecksHandler(selected);
    }

    const list = props.kafkaChecks.map((check) =>
        <Button className={classes.button} onClick={() => onKafkaCheckClick(check)} active={selectedCheck === check}>
            {check}
        </Button>
    );
    return (
        <ListGroup className={classes.brand}>
            <p> Available Kafka Checks</p>
            {list}
        </ListGroup>
    );
}

export default ListOfKafkaChecks;