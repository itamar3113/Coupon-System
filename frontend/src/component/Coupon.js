import { useLocation } from "react-router-dom"
import { useHistory } from "react-router-dom"
import Card from "./Card"

const Coupon = (props) => {

    const url = useLocation()
    const history = useHistory()

    const style = {
        backgroundColor: 'brown',
        fontSize: '12',
        border: '1'
    }

    const purchaseCoupon = async () => {
        try {
            const response = await fetch('http://localhost:8080/api//customers/purchase/' +
                props.id + '?token=' + localStorage.getItem('token'), {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            if (!response.ok) {
                if (response.status === 403) {
                    window.alert('30 minutes passed since your last action, please login again')
                    history.replace('/login')
                }
            }
            const data = await response.json()
            alert('coupon ' + props.title + ' added to your coupons')
            props.change(props.id)
            console.log('a')
        } catch (error) {
            console.log(error);
        }
    }

    const deleteCoupon = () => {
        if (window.confirm('Are you sure you want to delete this coupon?')) {
            try {
                const response = fetch('http://localhost:8080/api//companies/delete-coupon/' +
                    props.id + '?token=' + localStorage.getItem('token'), {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                })
                if (!response.ok) {
                    if (response.status === 403) {
                        window.alert('30 minutes passed since your last action, please login again')
                        history.replace('/login')
                    }
                }
                alert('Coupon ' + props.title + ' deleted')
                props.change(props.id)
            } catch (error) {
                console.log(error)
            }
        }
    }

    let purchaseOrDelete = null
    if ((url.pathname == '/customer/my_coupons' || url.pathname == '/customer/expired_in_week')) {
//
    } else {
        if (localStorage.getItem('clientType') === '/customer') {
            if (props.amount === 0) {
                purchaseOrDelete = <p style={style}>Sorry, this coupon is out of stock</p>
            } else {
                purchaseOrDelete = <button onClick={purchaseCoupon} style={style}>Purchase</button>
            }
        } else {
            purchaseOrDelete = <button onClick={deleteCoupon} style={style}>Delete</button>
        }
    }

    return (
        <Card>
            <li>
                <h2>{props.title}</h2>
                <img src={props.imageUrl} width="200" alt={props.name} />
                <h3>Price: {props.price}</h3>
                <h5>Start Date: {props.startDate}</h5>
                <h5>End date: {props.endDate}</h5>
                <p>{props.description}</p>
                {purchaseOrDelete}
            </li>
        </Card>
    )
}

export default Coupon

