import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEdit, faTrashAlt } from '@fortawesome/free-solid-svg-icons';
import './Convert.css';

function Convert() {
   const [project_id, setProjectId] = useState('');
   const [total_amount, setTotalAmount] = useState('');
   const [schedule_amount, setScheduleAmount] = useState('');
   const [payment_due_date, setPaymentDueDate] = useState('');
   const [is_paid, setIsPaid] = useState(false);
   const [payment_date, setPaymentDate] = useState('');
   const [errorMessage, setErrorMessage] = useState('');
   const [schedules, setSchedules] = useState([]);
   const [view, setView] = useState(false);
   const [editingId, setEditingId] = useState(null); 
   useEffect(() => {
      if (view) {
         const fetchSchedules = async () => {
            try {
               const response = await axios.get('http://localhost:8080/schedule/view/all');
               setSchedules(response.data);
            } catch (err) {
               setErrorMessage('Failed to fetch schedules');
            }
         };

         fetchSchedules();
      }
   }, [view]);

   const handleSubmit = async (e) => {
      e.preventDefault();
      setErrorMessage('');

      const schedule = {
         project_id,
         total_amount,
         schedule_amount,
         payment_due_date,
         is_paid,
         payment_date

      };
      console.log(schedule);

      try {
         if (editingId) {
            const response = await fetch(`http://localhost:8080/schedule/edit/${editingId}`, {
               method: 'PUT',
               headers: {
                  'Content-Type': 'application/json'
               },
               body: JSON.stringify(schedule)
            });

            if (response.ok) {
               alert('Payment schedule updated successfully');
               setSchedules(schedules.map(s => s.id === editingId ? { ...s, ...schedule } : s));
               setEditingId(null); 
            } else {
               const errorText = await response.text();
               setErrorMessage(errorText || 'Failed to update payment schedule');
            }
         } else {
            const response = await fetch('http://localhost:8080/schedule/create', {
               method: 'POST',
               headers: {
                  'Content-Type': 'application/json'
               },
               body: JSON.stringify(schedule)
            });

            if (response.ok) {
               alert('Payment schedule created successfully');
               const newSchedule = await response.json();
               setSchedules([...schedules, newSchedule]);
            } else {
               const errorText = await response.text();
               setErrorMessage(errorText || 'Failed to create payment schedule');
            }
         }
         setProjectId('');
         setTotalAmount('');
         setScheduleAmount('');
         setPaymentDueDate('');
         setIsPaid(false);
         setPaymentDate('');

      } catch (error) {
         console.error('Error:', error);
      }
   };

   const onEdit = (schedule) => {
      setProjectId(schedule.project_id);
      setTotalAmount(schedule.total_amount);
      setScheduleAmount(schedule.schedule_amount);
      setPaymentDueDate(schedule.payment_due_date);
      setIsPaid(schedule.is_paid);
      setPaymentDate(schedule.payment_date);
      setEditingId(schedule.id);
   };

   const onDelete = async (id) => {
      try {
         await axios.delete(`http://localhost:8080/schedule/delete/${id}`);
         setSchedules(schedules.filter(schedule => schedule.id !== id));
      } catch (err) {
         console.error('Error deleting schedule:', err);
         setErrorMessage('Failed to delete the schedule');
      }
   };

   return (
      <div className="container">
         <h1>Payment Schedule</h1>
         <form id="form" onSubmit={handleSubmit}>
            <div className="input-group">
               <label htmlFor="project_id">Project ID</label>
               <input type="text" id="project_id" name="project_id" placeholder="Enter Project ID" value={project_id} onChange={(e) => setProjectId(e.target.value)} />
            </div>
            <div className="input-group">
               <label htmlFor="total_amount">Total Amount</label>
               <input type="text" id="total_amount" name="total_amount" placeholder="Enter Total Amount" value={total_amount} onChange={(e) => setTotalAmount(e.target.value)} />
            </div>
            <div className="input-group">
               <label htmlFor="schedule_amount">Schedule Amount</label>
               <input type="text" id="schedule_amount" name="schedule_amount" placeholder="Enter Schedule Amount" value={schedule_amount} onChange={(e) => setScheduleAmount(e.target.value)} />
            </div>
            <div className="input-group">
               <label htmlFor="payment_due_date">Payment Due Date</label>
               <input type="date" id="payment_due_date" name="payment_due_date" value={payment_due_date} onChange={(e) => setPaymentDueDate(e.target.value)} />
            </div>
            <div className="input-group">
               <label htmlFor="is_paid">Is Paid</label>
               <input type="checkbox" id="is_paid" name="is_paid" checked={is_paid} onChange={(e) => setIsPaid(e.target.checked)} />
            </div>
            <div className="input-group">
               <label htmlFor="payment_date">Payment Date</label>
               <input type="date" id="payment_date" name="payment_date" value={payment_date} onChange={(e) => setPaymentDate(e.target.value)} />
            </div>
            <button type="submit">{editingId ? 'Update' : 'Save'}</button>
         </form>

         <div>
            <button type="button" onClick={() => setView(!view)}>
               {view ? 'Hide' : 'View'} Schedules
            </button>
            {view && (
               <table style={{ borderCollapse: 'collapse', width: '100%' }}>
                  <thead>
                     <tr>
                        <th style={{ border: '1px solid black', padding: '8px' }}>Total Amount</th>
                        <th style={{ border: '1px solid black', padding: '8px' }}>Schedule Amount</th>
                        <th style={{ border: '1px solid black', padding: '8px' }}>Payment Due Date</th>
                        <th style={{ border: '1px solid black', padding: '8px' }}>Is Paid</th>
                        <th style={{ border: '1px solid black', padding: '8px' }}>Payment Date</th>
                        <th style={{ border: '1px solid black', padding: '8px' }}>Actions</th>
                     </tr>
                  </thead>
                  <tbody>
                     {schedules.map(schedule => (
                        <tr key={schedule.id}>
                           <td style={{ border: '1px solid black', padding: '8px' }}>{schedule.total_amount}</td>
                           <td style={{ border: '1px solid black', padding: '8px' }}>{schedule.schedule_amount}</td>
                           <td style={{ border: '1px solid black', padding: '8px' }}>{schedule.payment_due_date}</td>
                           <td style={{ border: '1px solid black', padding: '8px' }}>{schedule.is_paid ? 'Yes' : 'No'}</td>
                           <td style={{ border: '1px solid black', padding: '8px' }}>{schedule.payment_date}</td>
                           <td style={{ border: '1px solid black', padding: '8px' }}>
                              <button onClick={() => onEdit(schedule)} style={{ marginRight: '8px' }}>
                                 <FontAwesomeIcon icon={faEdit} />
                              </button>
                              <button onClick={() => onDelete(schedule.id)}>
                                 <FontAwesomeIcon icon={faTrashAlt} />
                              </button>
                           </td>
                        </tr>
                     ))}
                  </tbody>
               </table>
            )}
         </div>

         {errorMessage && (
            <div className="error-message">{errorMessage}</div>
         )}
      </div>
   );
}

export default Convert;