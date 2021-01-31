import { useEffect, useState } from 'react';
import { Service } from '../types/Service';
import { Employee } from '../types/Employee';

export interface Employees {
  results: Employee[];
}

const usePostEmployeeService = () => {
  const [result, setResult] = useState<Service<Employees>>({
    status: 'loading'
  });

  useEffect(() => {
    fetch('http://localhost:8080/employees')
      .then(response => response.json())
      .then(response => setResult({ status: 'loaded', payload: { results: response }}))
      .catch(error => setResult({ status: 'error', error }));
  }, []);

  return result;
};

export default usePostEmployeeService;