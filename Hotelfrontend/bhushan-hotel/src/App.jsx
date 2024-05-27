import React from "react";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "/node_modules/bootstrap/dist/js/bootstrap.min.js";
import ExistingRooms from "./Components/room/ExistingRooms";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./Components/home/Home";
import EditRoom from "./Components/room/EditRoom";
import AddRoom from "./Components/room/AddRoom";
import NavBar from "./Components/layout/NavBar";
import Footer from "./Components/layout/Footer";
import RoomListing from "./Components/room/RoomListing";
import Admin from "./Components/admin/Admin";
import Checkout from "./Components/booking/Checkout";
import BookingSuccess from "./Components/booking/BookingSuccess";
import Bookings from "./Components/booking/Bookings";
import FindBooking from "./Components/booking/FindBooking";
import Login from "./Components/auth/Login";
import Registration from "./Components/auth/Registration";
import Profile from "./Components/auth/Profile";
import { AuthProvider } from "./Components/auth/AuthProvider";
import RequireAuth from "./Components/auth/RequireAuth";

function App() {
  return (
    <AuthProvider>
      <main>
        <Router>
          <NavBar />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/edit-room/:roomId" element={<EditRoom />} />
            <Route path="/existing-rooms" element={<ExistingRooms />} />
            <Route path="/add-room" element={<AddRoom />} />

            <Route
              path="/book-room/:roomId"
              element={
                <RequireAuth>
                  <Checkout />
                </RequireAuth>
              }
            />
            <Route path="/browse-all-rooms" element={<RoomListing />} />

            <Route path="/admin" element={<Admin />} />
            <Route path="/booking-success" element={<BookingSuccess />} />
            <Route path="/existing-bookings" element={<Bookings />} />
            <Route path="/find-booking" element={<FindBooking />} />

            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Registration />} />

            <Route path="/profile" element={<Profile />} />
            <Route path="/logout" element={<FindBooking />} />
          </Routes>
        </Router>
        <Footer />
      </main>
    </AuthProvider>
  );
}

export default App;
