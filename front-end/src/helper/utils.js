export function formatCurrency(number){
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(number);
}
export function convertStringToDate(dateString) {
    const [date, time] = dateString.split(' ');
    const [day, month, year] = date.split('/');
    const [hour, minute, second] = time.split(':');
  
    return new Date(year, month - 1, day, hour, minute, second);
  }