import { NavLink } from "react-router-dom";
import classes from './MainHeader.module.css'
import Logout from "../Logout";
const CompanyHeader = () => {

  return (
        <header>
            <nav>
                <ul>
                    <div class ='navbar'>
                        <li> <NavLink activeClassName={classes.active} to="/company/my_coupons">My Coupons</NavLink> </li>
                        <li> <NavLink activeClassName={classes.active} to="/company/create_coupon">Create Coupon</NavLink> </li>
                        <li> <NavLink activeClassName={classes.active} to="/company/company_details">Update Company Details</NavLink> </li>
                        <Logout />
                    </div>
                </ul>
            </nav>
        </header>
    )
}

export default CompanyHeader;