import Coupon from "./Coupon";

const CouponList = (props) => {
    return (
        <ul>
            {props.coupons.map(coupon => <Coupon
                id={coupon.id}
                title={coupon.title}
                imageUrl={coupon.imageUrl}
                price={coupon.price}
                startDate={coupon.startDate}
                endDate={coupon.endDate}
                description={coupon.description}
                amount={coupon.amount}
                change={props.change}
            />
            )}
        </ul>
    )
}

export default CouponList