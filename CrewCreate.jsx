import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import './CrewCreate.css';

function CrewCreate() {
   const { crewId } = useParams();
   const [pkCrid, setPkCrid] = useState('');
   const [fkPhid, setFkPhid] = useState('');
   const [fkRoleid, setFkRoleid] = useState('');
   const [name, setName] = useState('');
   const [email, setEmail] = useState('');
   const [mobile, setMobile] = useState('');
   const [pan, setPan] = useState('');
   const [errorMessage, setErrorMessage] = useState('');
   const [errors, setErrors] = useState({});
   const [crews, setCrews] = useState([]);

   useEffect(() => {
      if (crewId) { 
         fetch(`http://localhost:8080/crew/view/${crewId}`)
               .then(response => response.json())
               .then(data => {
                  setPkCrid(data.crewId || '');  
                  setFkPhid(data.phId || '');  
                  setFkRoleid(data.roleId || '');  
                  setName(data.name || '');
                  setEmail(data.email || '');
                  setMobile(data.mobile || '');
                  setPan(data.pan || '');
               })
               .catch(error => console.error('Error fetching data:', error));
      }
   }, [crewId]);

   const validate = () => {
      const newErrors = {};

      let isValid = true;
      if (!name) {
         newErrors.name = 'Please enter your Name';
         isValid = false;
      } else if (name.length > 50) {
         newErrors.name = 'Name cannot exceed 50 characters';
         isValid = false;
      } else if (!/^[A-Za-z ]+$/.test(name)) {
         newErrors.name = 'Name must contain only letters and spaces';
         isValid = false;
      }

      if (!email) {
         newErrors.email = 'Please enter your Email Address';
         isValid = false;
      } else if (!/\S+@\S+\.\S+/.test(email)) {
         newErrors.email = 'Please enter a valid email address';
         isValid = false;
      } else if (email.length > 100) {
         newErrors.email = 'Email address cannot exceed 100 characters';
         isValid = false;
      }

      if (!mobile) {
         newErrors.mobile = 'Please enter your Mobile Number';
         isValid = false;
      } else if (!/^\d+$/.test(mobile)) {
         newErrors.mobile = 'Mobile number must contain only numbers';
         isValid = false;
      } else if (mobile.length !== 10) {
         newErrors.mobile = 'Your Mobile Number must be 10 characters long';
         isValid = false;
      }

      const panRegex = /^[A-Z]{5}[0-9]{4}[A-Z]{1}$/;
      if (!pan) {
         newErrors.pan = 'Please enter your PAN Number';
         isValid = false;
      } else if (pan.length !== 10) {
         newErrors.pan = 'Your PAN Number must be 10 characters long';
         isValid = false;
      } else if (!panRegex.test(pan)) {
         newErrors.pan = 'Please enter a valid PAN number';
         isValid = false;
      }

      setErrors(newErrors);
      return isValid;
   };

   const handleSubmit = async (e) => {
      e.preventDefault();
      setErrorMessage('');

      if (!validate()) {
         return;
      }

      const crew = {
         crewId: pkCrid, 
         phId: fkPhid,  
         roleId: fkRoleid,  
         name,
         email,
         mobile,
         pan
      };

      try {
         let response;
         if (crewId) {  
            response = await fetch(`http://localhost:8080/crew/editcrew/${crewId}`, {
               method: 'PUT',
               headers: {
                  'Content-Type': 'application/json'
               },
               body: JSON.stringify(crew)
            });
         } else {
            response = await fetch('http://localhost:8080/crew/addcrew', {
               method: 'POST',
               headers: {
                  'Content-Type': 'application/json'
               },
               body: JSON.stringify(crew)
            });
         }

         if (response.ok) {
            const result = await response.json();
            if (crewId) {
               alert('Crew updated successfully');
               setCrews(crews.map(c => c.crewId === crewId ? { ...c, ...crew } : c));
            } else {
               alert('Crew created successfully');
               setCrews([...crews, result]);
            }
            setPkCrid('');
            setFkPhid('');
            setFkRoleid('');
            setName('');
            setEmail('');
            setMobile('');
            setPan('');
         } else {
            const errorText = await response.text();
            setErrorMessage(errorText || 'Failed to process request');
         }
      } catch (error) {
         console.error('Error:', error);
         setErrorMessage('An unexpected error occurred');
      }
   };

   return (
      <div className="container">
         <h1>{crewId ? 'Edit Crew' : 'Create Crew'}</h1>
         <form id="form" onSubmit={handleSubmit}>
            <div className="input-group">
               <label htmlFor="pkCrid">Crew ID:</label>
               <input type="text" id="pkCrid" name="pkCrid" placeholder="Enter Crew ID" value={pkCrid} onChange={(e) => setPkCrid(e.target.value)} required/>
            </div>
            <div className="input-group">
               <label htmlFor="fkPhid">Photographer ID:</label>
               <input type="text" id="fkPhid" name="fkPhid" placeholder="Enter Photographer ID" value={fkPhid} onChange={(e) => setFkPhid(e.target.value)} required/>
            </div>
            <div className="input-group">
               <label htmlFor="fkRoleid">Role:</label>
               <input type="text" id="fkRoleid" name="fkRoleid" placeholder="Enter Role" value={fkRoleid} onChange={(e) => setFkRoleid(e.target.value)} required />
            </div>
            <div className="input-group">
               <label htmlFor="name">Name:</label>
               <input type="text" id="name" name="name" placeholder="Enter Name" value={name} onChange={(e) => setName(e.target.value)} required/>
               {errors.name && <div className="error-message">{errors.name}</div>}
            </div>
            <div className="input-group"> 
               <label htmlFor="email">Email:</label>
               <input type="email" id="email" name="email" placeholder="Enter Email" value={email} onChange={(e) => setEmail(e.target.value)} required/>
               {errors.email && <div className="error-message">{errors.email}</div>}
            </div>
            <div className="input-group">
               <label htmlFor="mobile">Mobile:</label>
               <input type="text" id="mobile" name="mobile" placeholder="Enter Mobile" value={mobile} onChange={(e) => setMobile(e.target.value)} required/>
               {errors.mobile && <div className="error-message">{errors.mobile}</div>}
            </div>
            <div className="input-group">
               <label htmlFor="pan">PAN:</label>
               <input type="text" id="pan" name="pan" placeholder="Enter PAN" value={pan} onChange={(e) => setPan(e.target.value)} required/>
               {errors.pan && <div className="error-message">{errors.pan}</div>}
            </div>
            <button type="submit">{crewId ? 'Update' : 'Save'}</button>
            <Link to="/viewcrew"><button type="button">View</button></Link>
            {errorMessage && (
               <div className="error-message">{errorMessage}</div>
            )}
         </form>
      </div>
   );
}

export default CrewCreate;


