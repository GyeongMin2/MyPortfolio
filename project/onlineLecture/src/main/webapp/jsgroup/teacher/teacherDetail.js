/**
 * 
 */
function submitForm(actionUrl) {
  const form = document.getElementById("lectureForm");
  form.action = actionUrl;
  form.submit();
}
