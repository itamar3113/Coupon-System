import './App.css';
import Login from './pages/login';
import Customer from './pages/Customer';
import Company from './pages/Company';
import { Route, Redirect } from 'react-router-dom';
import { useSelector } from 'react-redux';
import bootstrap from 'bootstrap'

function App() {

  const token = useSelector(state => state.auth.token)

  return (
    <div className="App">

<Route path='/' exact>
        <Redirect to='/login' />
      </Route>

      <Route path='/login'>
        <Login />
      </Route>

      <Route path='/customer'>
        <Customer />
      </Route>

      <Route path='/company'>
        <Company />
      </Route>

      <Route path='/company' render={() => token ? <Redirect to='/company' /> : <Redirect to='/login' />} />

      <Route path='/customer' render={() => token ? <Redirect to='/customer' /> : <Redirect to='/login' />} />

    </div>
  )
}
export default App;
