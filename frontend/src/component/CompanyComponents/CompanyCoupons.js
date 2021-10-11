import { useState, useEffect } from 'react';
import CouponList from '../CouponList';
import { useHistory } from "react-router-dom";

const CompanyCoupons = () => {
    const [coupons, setCoupons] = useState([])
    const [isLoading, setLoading] = useState(false)
    const [error, setError] = useState(null)
    const [sortBy, setSortBy] = useState('endDate')
    const [couponId, setCouponId] = useState(0)
    const history = useHistory()

    const fetchMyCoupons = async () => {
        setLoading(true)
        setError(null)

        try {
            const response = await fetch('http://localhost:8080/api//companies/coupons?token=' + localStorage.getItem('token'), {
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
            data.sort((couponA, couponB) => {
                return sortCoupons(couponA, couponB, sortBy);
            }, setCoupons(data)
            )

        } catch (error) {
            console.log(error);
            setError(error.message)
        }
        setLoading(false)
    }

    let content = <p>No coupons to display</p>
    if (isLoading) {
        content = 'Loading...'
    } else if (error) {
        content = error
    } else if (coupons.length > 0) {
        content = <CouponList coupons={coupons} change={setCouponId} />
    }

    useEffect(() => { fetchMyCoupons(); }, [sortBy,couponId])

    const handleChangeSortBy = (e) => {
        console.log(e.target.value)
        setSortBy(e.target.value)
    }

    function sortCoupons(coupon1, coupon2, sortBy) {
        const c1 = coupon1[sortBy]
        const c2 = coupon2[sortBy]
        if (c1 > c2) {
            return 1;
        } else if (c1 < c2) {
            return -1;
        }
        return 0;
    }

    return (
        <>
            <h3>Sort By:</h3>
            <div className="radio">
                <label>
                    <input type="radio" value='title'
                        onChange={handleChangeSortBy} checked={sortBy === 'title'} />
                    Title
                </label>
            </div>
            <div className="radio">
                <label>
                    <input type="radio" value='startDate'
                        onChange={handleChangeSortBy} checked={sortBy === 'startDate'} />
                    Start Date
                </label>
            </div>
            <div className="radio">
                <label>
                    <input type="radio" value='endDate'
                        onChange={handleChangeSortBy} checked={sortBy === 'endDate'} />
                    End Date
                </label>
            </div>
            <br />
            <br />
            {content}
        </>
    )
}

export default CompanyCoupons
