import React from 'react';
import useEmployeesService from '../services/useEmployeesService';

const Employees: React.FC<{}> = () => {
  const service = useEmployeesService();

  return (
    <div>
      {service.status === 'loading' && <div>Loading...</div>}
      {service.status === 'loaded' &&
        service.payload.results.map(employee => (
          <div key={employee.id}>{employee.name} - {employee.role}</div>
        ))}
      {service.status === 'error' && (
        <div>Error, the backend moved to the dark side.</div>
      )}
    </div>
  );
};

export default Employees;
