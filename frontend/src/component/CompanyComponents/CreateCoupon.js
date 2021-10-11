import { useRef } from "react";
import { useHistory } from "react-router-dom";

const CreateCoupon = () => {

    const titleRef = useRef(null)
    const startDateRef = useRef(null)
    const endDateRef = useRef(null)
    const categoryRef = useRef(null)
    const amountRef = useRef(null)
    const descriptionRef = useRef(null)
    const priceRef = useRef(null)
    const imageUrlRef = useRef(null)
    const history = useHistory()
   


    const handleUpdateSubmituon = async (event) => {
        event.preventDefault()

        const title = titleRef.current.value
        const startDate = startDateRef.current.value
        const endDate = endDateRef.current.value
        const category = categoryRef.current.value
        const amount = amountRef.current.value
        const description = descriptionRef.current.value
        const price = priceRef.current.value
        const imageUrl = imageUrlRef.current.value

        try {
            const response = await fetch('http://localhost:8080/api/companies/add-coupon?token=' + localStorage.getItem('token'), {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ title, startDate, endDate, category, amount, description, price, imageUrl })
            })
            if (!response.ok) {
                if (response.status === 403) {
                    throw new Error('30 minutes passed since your last action please login again')
                }
                alert("The coupon added successfully")
                var form = document.getElementById('couponForm');
                form.reset();
            }
        } catch (error) {
            window.alert(error.message)
            history.replace('/login')
        }
        alert("The coupon added successfully")
        var form = document.getElementById('couponForm');
        form.reset();
    }

    return (
        <form id='couponForm' onSubmit={handleUpdateSubmituon}>
            <h2>Title:</h2>
            <input type="title" ref={titleRef} />
            <h2>Start Date: </h2>
            <input type="startDate" ref={startDateRef} defaultValue={'yyyy-mm-dd'} />
            <h2>End Date: </h2>
            <input type="endDate" ref={endDateRef} defaultValue={'yyyy-mm-dd'}/>
            <h2>Category:</h2>
            <input type="category" ref={categoryRef} />
            <h2>amount:</h2>
            <input type="amount" ref={amountRef} />
            <h2>Description</h2>
            <input type="description" ref={descriptionRef} />
            <h2>Price:</h2>
            <input type="price" ref={priceRef} />
            <h2>Image Url:</h2>
            <input type="imageUrl" ref={imageUrlRef} />
            <br />
            <br />
            <button type="submit">add coupon</button>
            <br />
            <br />
        </form>
    )
}

export default CreateCoupon
