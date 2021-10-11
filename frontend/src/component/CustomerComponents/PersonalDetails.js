
import { useState, useEffect, useRef } from 'react';
import { useHistory } from "react-router";

const PersonalDetails = () => {

    const firstNameRef = useRef(null)
    const lastNameRef = useRef(null)
    const passwordRef = useRef(null)
    const history = useHistory()
    const [details, setDetails] = useState([])

    let currentFirstName = details.firstName
    let currentLastName = details.lastName
    let currentEmail = details.email
    let currentPassword = details.password


    const handleUpdateSubmituon = async (event) => {
        event.preventDefault()

        const firstName = firstNameRef.current.value
        const lastName = lastNameRef.current.value
        const email = details.email
        const password = passwordRef.current.value


        try {
            const response = await fetch('http://localhost:8080/api/customers/update-customer?token=' + localStorage.getItem('token'), {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ firstName, lastName, email, password })
            })
            if (!response.ok) {
                throw new Error(response.stringify)
            }
            alert("Your personal detail changed successfully")
        } catch (error) {
            console.log(error.message)
        }
    }




    const currentDetails = async () => {
        try {
            const response = await fetch('http://localhost:8080/api//customers?token=' + localStorage.getItem('token'), {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            if (!response.ok) {
                if (response.status === 403) {
                    throw new Error('30 minutes passed since your last action please login again')
                }
            }
            const data = await response.json()
            setDetails(data)
            currentFirstName = details.firstName
            currentLastName = details.lastName
            currentEmail = details.email
            currentPassword = details.password
        } catch (error) {
            window.alert(error.message)
            history.replace('/login')
        }
    }

    useEffect(() => { currentDetails(); }, ([]))


    return (
        <form onSubmit={handleUpdateSubmituon}>
            <h2>First Name:</h2>
            <input type="firstName" ref={firstNameRef} defaultValue={currentFirstName} />
            <h2>Last Name:</h2>
            <input type="lasttName" ref={lastNameRef} defaultValue={currentLastName} />
            <h2>Email: {currentEmail}</h2>
            <h2>Password:</h2>
            <input type="password" ref={passwordRef} defaultValue={currentPassword} />
            <br />
            <br />
            <button type="submit">update</button>
        </form>
    )
}

export default PersonalDetails
