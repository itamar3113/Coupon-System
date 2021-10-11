import CompanyHeader from '../component/MainHeader/CompanyHeader';
import { Route, Redirect, Switch } from 'react-router-dom';
import CompanyCoupons from '../component/CompanyComponents/CompanyCoupons'
import CompanyDetails from '../component/CompanyComponents/CompanyDetails'
import CreateCoupon from '../component/CompanyComponents/CreateCoupon'

function Company() {
    return (
        <>
            <CompanyHeader />
           
            <Switch>
                <Route path='/company' exact>
                    <Redirect to='/company/my_coupons' />
                </Route>
                <Route path='/company/my_coupons'>
                    <CompanyCoupons />
                </Route>
                <Route path='/company/company_details'>
                    <CompanyDetails />
                </Route>
                <Route path='/company/create_coupon'>
                    <CreateCoupon />
                </Route>
            </Switch>
        </>
    )
}

export default Company