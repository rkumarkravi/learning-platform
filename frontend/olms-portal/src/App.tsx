import { Outlet } from 'react-router-dom'
import './App.css'
import { useData } from './main';
// import { Button } from "@/components/ui/button"

function App() {
  const { data, setData } = useData();
  return (
    <div>
        <Outlet />
      </div>
  )
}

export default App
