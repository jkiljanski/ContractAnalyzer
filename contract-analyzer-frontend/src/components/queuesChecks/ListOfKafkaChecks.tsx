import React, {useState} from "react";
import {Button, ListGroup} from "reactstrap";
import classes from "../Styles.module.css";


interface Props {
    kafkaChecksToRun: string[] | null
    checkHandler: (nameOfCheck: string) => void
}

const ListOfKafkaChecks: React.FC<Props> = (props: Props) => {

    const [selectedCheck, setSelectedCheck] = useState('');

    const onKafkaCheckClick = (selected: string) => {
        setSelectedCheck(selected);
        props.checkHandler(selected);
    }

    const list = props.kafkaChecksToRun!.map((check) =>
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