/**
 * @author Jesse van Bree
 * @desc This class reads the wii controller input and returns it to the given function
 */

// libraries
import React, { Component } from 'react';
import openSocket from 'socket.io-client';

// Config
import {SOCKET_ON_RECIEVE, SOCKET_IP} from '../../constants';

export default class ControllerReader extends Component {
    state = {
        currentButton: null,
        buttonHistory: []
    }
    
    constructor(props) {
        super(props);

        const socket = openSocket(SOCKET_IP);
        // Get wii controller input
        socket.on(SOCKET_ON_RECIEVE, (msg) => {
            this.onButtonReceive(msg);
        });

        // Alert server that this app wants to get wii controller input
        socket.emit("controllers", "");
    }

    onButtonReceive = (msg) => {
        // Edit state variables to include new input
        this.setState({
            currentButton: msg,
            buttonHistory: this.state.buttonHistory.concat(msg)
        }, () => {
            // return the state variables to given function
            this.props.callback(this.state.currentButton, this.state.buttonHistory);
        })
    }

    render() {
        return (<div style={{display: "none"}}></div>)
    }    
}