import React from 'react';
import './App.css';
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