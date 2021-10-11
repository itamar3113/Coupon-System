
import CustomerHeader from '../component/MainHeader/CustomerHeader';
import { Route, Redirect, Switch } from 'react-router-dom';
import MyCoupons from '../component/CustomerComponents/MyCoupons';
import NotPurchased from '../component/CustomerComponents/NotPurchased';
import PersonalDetails from '../component/CustomerComponents/PersonalDetails';
import ExpiredInWeek from '../component/CustomerComponents/ExpiredInWeekCoupons';

function Customer() {
    return (
        <>
            <CustomerHeader />
            <Switch>
                <Route path='/customer' exact>
                    <Redirect to='/customer/my_coupons' />
                </Route>
                <Route path='/customer/my_coupons'>
                    <MyCoupons />
                </Route>
                <Route path='/customer/not_purchased'>
                    <NotPurchased />
                </Route>
                <Route path='/customer/personal_details'>
                    <PersonalDetails />
                </Route>
                <Route path='/customer/expired_in_week'>
                    <ExpiredInWeek />
                </Route>
            </Switch>
        </>
    )
}

export default Customer