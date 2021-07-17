import React from 'react';
import logo from './logo.svg';
import './App.css';
import Employees from './pages/Employee';
import Router from './router';
import { CssBaseline } from '@material-ui/core';

function App() {
  return (
    <React.Fragment>
      <CssBaseline />
      <Router />
    </React.Fragment>
  );
}

export default App;