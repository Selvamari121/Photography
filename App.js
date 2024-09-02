import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ScheduleCreate from './ScheduleCreate';
import ViewAllSchedule from './ViewAllSchedule';
import Convert from './Convert';

function App() {
    return (
        <Router>
            <Routes>
              <Route path="/" element={<Convert />} />
                <Route path="/create" element={<ScheduleCreate />} />
                <Route path="/view" element={<ViewAllSchedule />} />
            </Routes>
        </Router>
    );
}

export default App;











// // import Convert from "./Convert";
// import ScheduleCreate from "./ScheduleCreate";
// import ViewAllSchedule from "./ViewAllSchedule";
// function App() {
//   return (
//     // <Convert/>
//     <div>    
//       <ScheduleCreate/>
//       <ViewAllSchedule/>
//     </div>
//   );
// }

// export default App;
