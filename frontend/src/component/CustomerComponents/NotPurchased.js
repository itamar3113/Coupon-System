import { useState, useEffect } from 'react';
import CouponList from '../CouponList';
import { useHistory } from 'react-router';

const NotPurchased = () => {
    const [coupons, setCoupons] = useState([])
    const [isLoading, setLoading] = useState(false)
    const [error, setError] = useState(null)
    const history = useHistory()
    const [couponId, setCouponId] = useState(0)


    const fetchNotPurchasedCoupons = async () => {
        setLoading(true)
        setError(null)

        try {
            const response = await fetch('http://localhost:8080/api///customers/coupons/not-purchased?token=' + localStorage.getItem('token'), {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            if (!response.ok) {
                if (response.status === 403) {
                    window.alert('30 minutes passed since your last action please login again')
                    history.replace('/login')
                } else {
                    throw new Error(response.stringify)
                }
            }
            const data = await response.json()
            setCoupons(data)
        } catch (error) {
            setError(error.message)
            console.log(error);
        }
        setLoading(false)
    }

    useEffect(() => { fetchNotPurchasedCoupons(); }, [couponId])

    let content = <p>No coupons to display</p>
    if (isLoading) {
        content = 'Loading...'
    } else if (error) {
        content = error
    } else if (coupons.length > 0) {
        content = <CouponList coupons={coupons} change={setCouponId} />
    }

    return (
        <>
            {content}
        </>
    )
}

export default NotPurchased
