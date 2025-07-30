// ✅ src/App.js
import React from 'react';
import Home from './Home'; // ✅ Don't use curly braces {} for default exports

function App() {
  return (
    <div>
      <Home />
    </div>
  );
}

export default App;
