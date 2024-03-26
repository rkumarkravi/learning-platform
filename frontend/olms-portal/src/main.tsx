import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import AuthLayout from './pages/auth/AuthLayout.tsx';

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    // errorElement: <ErrorPage />,
    children: [
      {
        element: <AuthLayout />,
        index: true
      },
      // {
      //   element: <CreateRecipeV3 />,
      //   path:"/create-your-recipe"
      // },{
      //   element: <ViewRecipe />,
      //   path:"/view-recipe/:recipeId",
      //   loader: async ({ params }) => {
      //     return fetch(`/recipes/${params.recipeId}`);
      //   },
      // },
    ],
  },
]);
ReactDOM.createRoot(document.getElementById('root')!).render(
  // <React.StrictMode>
  //   <App />
  // </React.StrictMode>,
  <RouterProvider router={router}/>
)
