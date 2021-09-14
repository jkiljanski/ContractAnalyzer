import React from "react";
import {Badge} from "reactstrap";

interface Props {
    count: number
    onClick: () =>void

}

const Counter: React.FC<Props> = (props:Props) => {

    let number= props.count;

    return (
    <Badge>{number}</Badge>
)
}

export default Counter