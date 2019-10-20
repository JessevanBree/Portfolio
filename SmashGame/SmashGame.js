/**
 * @author: Jesse van Bree
 * @description: this is the smash the controller game
 */

// Libraries
import React, {Component} from 'react';
import {browserHistory} from 'react-router';
import openSocket from 'socket.io-client';
import Cookies from 'universal-cookie';

// CSS
import 'bootstrap/dist/css/bootstrap.css';
import './SmashGame.css';

// Components
import ControllerReader from "../GlobalComponents/ControllerReader/ControllerReader";
import StartGameForm from '../GlobalComponents/StartGameForm/StartGameForm';
import bg from "../img/Kop-van-Jut.png";
import $ from 'jquery';

class SmashGame extends Component {
    state = {
        started: null,
        filerHeight: null,
        fillerColor: "white",
        score: 0,
        points: 0,
        user: null
    }

    constructor(props) {
        super(props);
        this.socket = openSocket('http://51.68.190.129:5000');
        this.cookies = new Cookies();
        let account = this.cookies.get("account");
        let controller = this.cookies.get("wiiController");
        let difficulty = this.cookies.get("difficulty");
        
        // Check if the user is logged in and if the other cookies are set
        if(account === undefined && controller === undefined && difficulty === undefined) {
            browserHistory.push("/Home");
        } else if(account === undefined) {
            browserHistory.push("/AccountSelect");            
        } else if(controller === undefined) {
            browserHistory.push("/ControllerSelect");            
        } else if(difficulty === undefined) {
            browserHistory.push("/DifficultySelect");
        }
        this.state.user = account;
        this.difficulty = parseInt(difficulty);
        this.state.score = account !== undefined ? account.score : 0;
        this.state.filerHeight = "0%";
        this.state.started = false;
    }

    calculateBarPercentage(velocity) {
        // Lowest: 35 / Highest: ~150
        const MIN_VELOCITY = 35;
        const MAX_VELOCITY = [65, 75, 85, 100];
        let mv_index =  0;
        console.log(this.difficulty)
        let barPercentage = ((velocity - MIN_VELOCITY) * 100) / (MAX_VELOCITY[mv_index] - MIN_VELOCITY);

        return Math.round(barPercentage);
    }

    static calculateScore(percentage) {
        const SCORE_MULTIPLIER = 100;
        return SCORE_MULTIPLIER * percentage;
    }

    /**
     * Start logic for the game
     */
    start = () => {
        this.socket.emit("switchmodes", "1");

        this.setState({
            started: true
        }, () => {
            console.log("started the game");
        });
    }

    stop = (percentage) => {
        console.log(percentage);
        let points = SmashGame.calculateScore(percentage);
        console.log(points, percentage);
        this.setState({
            started: false,
            filerHeight: 100,
            fillerColor: "white",
            points: parseInt(points),
            score: (this.state.score !== NaN ? this.state.score : 0) + (points !== NaN ? points : 0)
        }, () => {
            console.log("stopped the game");
            $.post("http://51.68.190.129:5000/addscore", {'game': 2,'score': this.state.score, 'user': this.state.user.id,'token': 'Yjdfm3jbCj'});

        });
    }

    fillBar = (velocity) => {
        for(let i = 35; i <= velocity; i++) {
            setTimeout(() => {
                let percentage = this.calculateBarPercentage(i);
                percentage = 100 - percentage;
                this.setState({
                    filerHeight: percentage.toString() +"%",
                    fillerColor: "rgb(80, 240, 80)"
                });
            },10)
        }
        setTimeout(() => {
            console.log(velocity, this.calculateBarPercentage(velocity))
            this.stop(this.calculateBarPercentage(velocity));
        }, 750)
    }

    controllerReceive = (button, buttonHistory) => {

        if(this.state.started === false) {
            switch(button.toLowerCase()){
                case "a":
                    this.start();
                    break;
                default:
                    // console.log("geen key") 
            }
        } else {
            if(!isNaN(button)){
                this.fillBar(parseInt(button));
            }
        }
    }

    // Renders the component
    render() {
        return (
            <div className="container text-center">
                <ControllerReader callback={this.controllerReceive}/>
                <h2 className="h2">Kop van jut</h2>
                <h4 className="h4 text-muted mb-2" style={{display: !this.state.started ? "block" : "none"}}>Klik op start om het spel te spelen</h4>
                <h4 className="h4 text-muted" style={{display: this.state.started ? "block" : "none"}}>Sla zo hard mogelijk met de controller in de lucht</h4>
                <h4 className="h2">Score deze ronde: {Math.floor(this.state.points)}</h4>
                <h4 className="h2">Score: {Math.floor(this.state.score)}</h4>
                <StartGameForm start={this.start} started={this.state.started} />
                <div style={{display: this.state.started ? "flex" : "none"}} className="smashGameContainer row mx-auto">
                    <div style={{display: this.state.started ? "flex" : "none"}} className="mx-auto row">
                        {/* <img className="bg-test" src={bg} height="50vh">
                        </img> */}
                            <div className="bar" style={{backgroundColor: this.state.fillerColor, display: this.state.started ? "flex" : "none"}}>
                                    <div className="filler" style={{height: this.state.filerHeight, maxHeight: "100%", display: this.state.started ? "flex" : "none"}}></div>
                                </div>
                            <div style={{display: this.state.started ? "flex" : "none"}} className="row m-0 ml-1" style={{height: "50vh", width: "15px"}}>
                                <span style={{display: this.state.started ? "flex" : "none"}} className="barNumber mb-auto">100</span>
                                <span style={{display: this.state.started ? "flex" : "none"}} className="barNumber my-auto">50</span>
                                <span style={{display: this.state.started ? "flex" : "none"}} className=" align-bottom mt-auto"><p className="mb-0">0</p></span>
                            </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default SmashGame;
