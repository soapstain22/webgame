const container = document.getElementById('container');

container.addEventListener('click', (event) => {
  // Get the click coordinates
  const x = event.clientX;
  const y = event.clientY;

  // Create a new circle element
  const circle = document.createElement('div');
  circle.classList.add('circle');

  // Set the circle's position
  circle.style.left = `${x}px`;
  circle.style.top = `${y}px`;

  // Append the circle to the container
  container.appendChild(circle);
});