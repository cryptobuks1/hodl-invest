import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import Home from './components/Home'
import About from './components/About'
import Login from './components/Login'
import Registration from './components/Registration'
import Dashboard from './components/Dashboard'
import NavigationBar from './components/NavigationBar'
import {connect} from 'react-redux';

class App extends Component {

  constructor(props) {
    super(props);
    this.onUpdateUser = this.onUpdateUser.bind(this);
    this.state = {
      token: '',
      username: ''
    }
  }
  onUpdateUser(){
    this.props.onUpdateUser('Sammy');
  }

 updateState(_token, _username, callback) {
   var callback = callback;
    if (!callback) {
      callback = null;
    }
    this.setState({
      token: _token,
      username: _username
    }, callback)
  }

  render() {
    return (
      <Router>
        <div>
          <NavigationBar />
          <Route exact path ="/" component={Home} />
          <Route path ="/login" render={()=><Login updateState={this.updateState.bind(this)} />} />
          <Route path ="/register" component={Registration} />
          <Route path ="/about" component={About} />
          <Route path ="/dashboard" render={()=><Dashboard token={this.state.token} />} />
        </div>
      </Router>
    );
  }
}

export default App;
