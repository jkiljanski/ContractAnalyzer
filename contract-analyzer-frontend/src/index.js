import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter} from "react-router-dom";
import { ReactKeycloakProvider } from '@react-keycloak/web'
import keycloak from './keycloak';

export const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const keycloakInitOptions = { onLoad: 'login-required' }

ReactDOM.render(
    <ReactKeycloakProvider authClient={keycloak} initOptions={keycloakInitOptions}>
    <BrowserRouter>
        <React.StrictMode>
            <App/>
        </React.StrictMode>
    </BrowserRouter>
        </ReactKeycloakProvider>
            ,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
