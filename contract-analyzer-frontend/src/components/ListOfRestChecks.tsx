import React, {useReducer, useState} from "react";
import {Button, ButtonGroup, ListGroup} from "reactstrap";
import classes from "./Styles.module.css";
import Counter from "./Counter";

const ListOfRestChecks = (props: { checkHandler: (listOfChecks: string[]) => void; checks: string[] | null; }) => {


    const [selectedChecks, setSelected] = useState(new Array<string>());


    const [, forceUpdate] = useReducer(x => x + 1, 0);

    const onResetButtonClick = (selected: string) => {

        let indices = [];
        let idx = selectedChecks.indexOf(selected)
        console.log(idx)
        while (idx !== -1) {
            indices.push(idx);
            idx = selectedChecks.indexOf(selected, idx + 1);
        }
        for (let i = indices.length - 1; i >= 0; i--)
            selectedChecks.splice(indices[i], 1);
        setSelected(selectedChecks)
        props.checkHandler(selectedChecks)
        forceUpdate();
    }

    const howManySelected = (check: string) => {
        return selectedChecks.filter(x => x === check).length
    }

    const onCheckClick = (selected: string) => {


        selectedChecks.push(selected);

        setSelected([...selectedChecks]);
        props.checkHandler(selectedChecks)
    }

    const list = props.checks?.map((check) =>
        <ButtonGroup><Button className={classes.button} onClick={() => onCheckClick(check)}
                             active={selectedChecks.includes(check)}>
            {check}<Counter onClick={() => onResetButtonClick(check)} count={howManySelected(check)}/>
        </Button>
            <button className={classes.resetButton} onClick={() => onResetButtonClick(check)}>×</button>
        </ButtonGroup>
    );
    return (
        <ListGroup className={classes.brand}>
            <p> Available Checks
            </p>
            {list}
        </ListGroup>
    );
}

export default ListOfRestChecks