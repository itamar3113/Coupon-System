import { useState, useEffect } from 'react';
import CouponList from '../CouponList';
import { useHistory } from "react-router-dom";

const MyCoupons = () => {
    const [coupons, setCoupons] = useState([])
    const [isLoading, setLoading] = useState(false)
    const history = useHistory()

    const fetchMyCoupons = async () => {
        setLoading(true)

        try {
            const response = await fetch('http://localhost:8080/api//customers/coupons?token=' + localStorage.getItem('token'), {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            if (!response.ok) {
                if (response.status === 403) {
                    window.alert('30 minutes passed since your last action, please login again')
                    history.replace('/login')
                } else {
                    throw new Error("No coupons to display")
                }
            }
            const data = await response.json()
            setCoupons(data)
        } catch (error) {
            console.log(error.message)
        }
        setLoading(false)
    }

    useEffect(() => { fetchMyCoupons(); }, ([]))

    let content = <p>No coupons to display</p>
    if (isLoading) {
        content = 'Loading...'
    } else if (coupons.length > 0) {
        content = <CouponList coupons={coupons} />
    }

    return (
        <>
            {content}
        </>
    )
}

export default MyCoupons
