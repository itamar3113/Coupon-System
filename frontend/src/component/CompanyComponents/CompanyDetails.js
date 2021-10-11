import { useRef } from "react";
import { useState, useEffect } from 'react';
import { useHistory } from "react-router-dom";

const CompanyDetails = () => {
    const nameRef = useRef(null)
    const passwordRef = useRef(null)
    const history = useHistory()
    const [details, setDetails] = useState([])

    let currentName = details.name
    let currentEmail = details.email
    let currentPassword = details.password

    const handleUpdateSubmituon = async (event) => {
        event.preventDefault()

        const name = nameRef.current.value
        const email = details.email
        const password = passwordRef.current.value



        await fetch('http://localhost:8080/api/companies/update-company?token=' + localStorage.getItem('token'), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ name, email, password })
        })
        alert("Your details changed successfully")
    }

    const currentDetails = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/companies?token=' + localStorage.getItem('token'), {
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
            currentName = details.lastName
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
            <h2>Name:</h2>
            <input type="name" ref={nameRef} defaultValue={currentName} />
            <h2>Email: {currentEmail}</h2>
            <h2>Password:</h2>
            <input type="password" ref={passwordRef} defaultValue={currentPassword} />
            <br />
            <br />
            <button type="submit">update</button>
        </form>
    )
}


export default CompanyDetails
