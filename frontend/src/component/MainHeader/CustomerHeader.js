import { NavLink } from "react-router-dom";
import classes from './MainHeader.module.css'
import Logout from "../Logout";

const CustomerHeader = () => {

    return (
        <header>
            <nav>
                <ul>
                    <div class='navbar' >
                        <li> <NavLink activeClassName={classes.active} to="/customer/my_coupons">My Coupons</NavLink> </li>
                        <li> <NavLink activeClassName={classes.active} to="/customer/not_purchased">Find Coupon</NavLink> </li>
                        <li> <NavLink activeClassName={classes.active} to="/customer/personal_details">Update Personal Details</NavLink> </li>
                        <li> <NavLink activeClassName={classes.active} to="/customer/expired_in_week">Expired In Week Coupons</NavLink> </li>
                        <Logout />
                    </div>
                </ul>
            </nav>
        </header>

    )
}

export default CustomerHeader;











