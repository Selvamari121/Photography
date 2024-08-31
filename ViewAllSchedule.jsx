import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEdit, faTrashAlt } from '@fortawesome/free-solid-svg-icons';

const ScheduleList = () => {
   const [schedules, setSchedules] = useState([]);
   const [loading, setLoading] = useState(true);
   const [error, setError] = useState(null);
   const [editMode, setEditMode] = useState(false);
   const [currentSchedule, setCurrentSchedule] = useState(null);
   const [formData, setFormData] = useState({
      total_amount: '',
      schedule_amount: '',
      payment_due_date: '',
      is_paid: false,
      payment_date: ''
   });

   useEffect(() => {
      const fetchSchedules = async () => {
         try {
            const response = await axios.get('http://localhost:8080/schedule/view/all');
            setSchedules(response.data);
         } catch (err) {
            setError(err);
         } finally {
            setLoading(false);
         }
      };

      fetchSchedules();
   }, []);

   const onEdit = (schedule) => {
      setCurrentSchedule(schedule);
      setFormData({
         total_amount: schedule.total_amount,
         schedule_amount: schedule.schedule_amount,
         payment_due_date: schedule.payment_due_date,
         is_paid: schedule.is_paid,
         payment_date: schedule.payment_date
      });
      setEditMode(true);
   };

   const handleInputChange = (e) => {
      const { name, value, type, checked } = e.target;
      setFormData({
         ...formData,
         [name]: type === 'checkbox' ? checked : value
      });
   };

   const handleSubmit = async (e) => {
      e.preventDefault();
      try {
         await axios.put(`http://localhost:8080/schedule/edit/${currentSchedule.id}`, formData);
         setSchedules(schedules.map(schedule => 
            schedule.id === currentSchedule.id ? { ...schedule, ...formData } : schedule
         ));
         setEditMode(false);
         setCurrentSchedule(null);
         setFormData({
            total_amount: '',
            schedule_amount: '',
            payment_due_date: '',
            is_paid: false,
            payment_date: ''
         });
      } catch (err) {
         console.error('Error updating schedule:', err);
      }
   };

   const onDelete = async (id) => {
      try {
         await axios.delete(`http://localhost:8080/schedule/delete/${id}`);
         setSchedules(schedules.filter(schedule => schedule.id !== id));
      } catch (err) {
         console.error('Error deleting schedule:', err);
      }
   };

   if (loading) return <p>Loading...</p>;
   if (error) return <p>Error fetching schedules: {error.message}</p>;

   return (
      <div>
         <h1>All Schedules</h1>
         {editMode && currentSchedule && (
            <div>
               <h2>Edit Schedule</h2>
               <form onSubmit={handleSubmit}>
                  <label>  Total Amount:
                     <input type="number" name="total_amount" value={formData.total_amount} onChange={handleInputChange}  required />
                  </label>
                  <br />
                  <label> Schedule Amount:
                     <input type="number" name="schedule_amount"  value={formData.schedule_amount} onChange={handleInputChange} required />
                  </label>
                  <br />
                  <label>
                     Payment Due Date:
                     <input  type="date" name="payment_due_date" value={formData.payment_due_date} onChange={handleInputChange}  required/>
                  </label>
                  <br />
                  <label>Is Paid:
                     <input type="checkbox"  name="is_paid"  checked={formData.is_paid}  onChange={handleInputChange}/>
                  </label>
                  <br />
                  <label> Payment Date:
                     <input type="date" name="payment_date" value={formData.payment_date}  onChange={handleInputChange} />
                  </label>
                  <br />
                  <button type="submit">Update</button>
                  <button type="button" onClick={() => setEditMode(false)}>Cancel</button>
               </form>
            </div>
         )}
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
      </div>
   );
};

function ViewAllSchedule(){
   return(
      <div>
         <ScheduleList/>
      </div>
   );
}

export default ViewAllSchedule;