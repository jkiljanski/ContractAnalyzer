import {Container, Nav, Navbar, NavbarBrand, Button} from "reactstrap";
import {NavLink} from "react-router-dom";
import '../App.css';
import classes from "./Styles.module.css";
import {useKeycloak} from "@react-keycloak/web";

const Navigation = (props) => {


    const {keycloak} = useKeycloak();

    const logout = () => {
        if(keycloak.authenticated)
        {keycloak.logout();}
        else
            alert("Your not logged in")
    }


    return (
        <Container className={classes.navigation} fluid>
            <Navbar light expand="md">
                <NavbarBrand className={classes.brand}>Contract Analyzer</NavbarBrand>
                <Nav className="mr-auto" navbar>
                    <Button className={classes.button} tag={NavLink} to="/rest/">
                        Rest checks
                    </Button>
                    <Button className={classes.button} tag={NavLink} to="/queues/" >
                        Queues checks
                    </Button>
                    <Button className={classes.button} tag={NavLink} to="/reports/" >
                        Reports
                    </Button>
                   <div className={classes.logoutButton}>
                       {}
                       <Button onClick={logout} >
                           Logout </Button>

                   </div>

                </Nav>

            </Navbar>
        </Container>
    )
}

export default Navigation