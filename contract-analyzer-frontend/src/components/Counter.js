import React from "react";
import {Badge} from "reactstrap";

const Counter = (props) => {

    let number= props.count;

    return (
    <Badge>{number}</Badge>
)
}

export default Counter