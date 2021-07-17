import React, { FC } from "react";
import {
  BrowserRouter,
  Switch,
  Route,
  Link
} from "react-router-dom";
import ComponentList from "./components/ComponentList";

const Router: FC = () => {
  return (
    <BrowserRouter>
      <Switch>
        <Route path="/lulu">
          <ComponentList />
        </Route>
      </Switch>
    </BrowserRouter>
  );
}

export default Router;