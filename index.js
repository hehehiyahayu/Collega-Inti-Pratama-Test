let selectedId = null;
let checkDuplicateData = null;

function fetchPelanggan(){
  fetch('http://localhost:8080/pelanggan/', {
      method: 'GET',
      headers:{"Content-Type":"application/json", 'Accept': 'application/json'}
  })
  .then((response) => {
      return response.json();
  })
  .then((data) => {
      const pelangganTable = document.getElementById('pelanggan_table');
      pelangganTable.innerHTML = '';

      data = data.data
      
      var nomor = 1

      data.forEach((element) => {
          const row = document.createElement('tr');
          row.innerHTML = `
          <td class="td_number">${element.idPelanggan}</td>
          <td>${element.nama}</td>
          <td>${element.alamat}</td>
          <td>${element.pekerjaan}</td>
          `;
          nomor = nomor + 1;
          row.addEventListener('dblclick', () => editPelanggan(element));
          pelangganTable.appendChild(row);
      });
  })
  .catch((error) => console.error('Error fetching pelanggan:', error));
}

function checkDuplicate(idPelanggan, id){
  fetch(`http://localhost:8080/pelanggan/check/${idPelanggan}/${id}`, {
      method: 'GET',
      headers:{"Content-Type":"application/json", 'Accept': 'application/json'}
  })
  .then((response) => {
    return response.json();
  })
  .then((data) => {
    if(data == true){
      checkDuplicateData = true;
      // return;
    }else{
      checkDuplicateData = false;
      // return;
    }
  })
  .catch((error) => console.error('Error fetching pelanggan:', error));
}

function editPelanggan(item){
  document.getElementById("id_data").value = item.id;
  document.getElementById("id_pelanggan_input").value = item.idPelanggan;
  document.getElementById("nama_input").value = item.nama;
  document.getElementById("alamat_input").value = item.alamat;

  if (item.jenisKelamin === 'Laki-laki') {
    document.getElementById('jenisKelaminLaki-laki').checked = true;
  } else {
      document.getElementById('jenisKelaminPerempuan').checked = true;
  }

  document.getElementById("umur_input").value = item.umur;
  document.getElementById("pekerjaan_input").value = item.pekerjaan;
  document.getElementById("penghasilan_input").value = item.penghasilan;

  selectedId = item.id;
}

function addPelanggan(){
  
  const selectedGender = document.querySelector('input[name="jenis_kelamin"]:checked')
  
  const id = document.getElementById("id_data").value;
  const idPelanggan = document.getElementById("id_pelanggan_input").value;
  const nama = document.getElementById("nama_input").value;
  const alamat = document.getElementById("alamat_input").value;
  const jenisKelamin = selectedGender ? selectedGender.value : '';
  const umur = document.getElementById("umur_input").value;
  const pekerjaan = document.getElementById("pekerjaan_input").value;
  const penghasilan = document.getElementById("penghasilan_input").value;

  
  if(idPelanggan == '' || nama == '' || alamat == '' || jenisKelamin == '' || umur == '' || pekerjaan == '' || penghasilan == ''){
    alert("Data belum terpenuhi, harus segera diisi dengan tuntas");
    return;
  }else if(umur < 0){
    alert("Data umur tidak boleh kurang dari 0");
    return;
  }else if(penghasilan < 0){
    alert("Data penghasilan tidak boleh kurang dari 0");
    return;
  }else if(idPelanggan < 0){
    alert("Data Id Pelanggan tidak boleh kurang dari 0");
    return;
  }
  
  checkDuplicate(idPelanggan, id);

  if(checkDuplicateData == true){
    alert("Id Pelanggan sudah digunakan");
    checkDuplicateData = null;
    return;
  }else{
    addDataProcess(idPelanggan, nama, alamat, pekerjaan, umur, jenisKelamin, penghasilan);
  }

}

function addDataProcess(idPelanggan, nama, alamat, pekerjaan, umur, jenisKelamin, penghasilan){
  if(selectedId){
    fetch(`http://localhost:8080/pelanggan/update/${selectedId}`, {
      method: 'PUT',
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
          idPelanggan,
          nama,
          alamat,
          pekerjaan,
          umur,
          jenisKelamin,
          penghasilan
      })
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok ' + response.statusText);
      }
      fetchPelanggan();
      clearForm();
    })
    .catch((error) => console.error('Error fetching pelanggan:', error));
  }else{
    fetch('http://localhost:8080/pelanggan/add', {
      method: 'POST',
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
          idPelanggan,
          nama,
          alamat,
          pekerjaan,
          umur,
          jenisKelamin,
          penghasilan
      })
    })
    .then(response => {
      if (!response.ok) {
          throw new Error('Network response was not ok ' + response.statusText);
      }
      fetchPelanggan();
      clearForm();
    })
    .catch(error => console.error('Error deleting data:', error));
  }
}

function deletePelanggan(){
  if(!selectedId){
    alert('Tidak ada data pelanggan yang dipilih');
    return;
  }

  fetch(`http://localhost:8080/pelanggan/delete/${selectedId}`, {
    method: 'DELETE',
    headers: {
      "Content-Type": "application/json",
    }
  })
  .then(response => {
    if(!response.ok){
      throw new Error('Network response was not ok ' + response.statusText);
    }
      fetchPelanggan();
      clearForm();
  })
  .catch(error => console.error('Error deleting data:', error));
}

function clearForm(){
  document.getElementById("id_data").value = '';
  document.getElementById("id_pelanggan_input").value = 0;
  document.getElementById("nama_input").value = '';
  document.getElementById("alamat_input").value = '';
  document.querySelector('input[name="jenis_kelamin"]:checked').checked = false;
  document.getElementById("umur_input").value = 0;
  document.getElementById("pekerjaan_input").value = 'ASN';
  document.getElementById("penghasilan_input").value = 0;
  selectedId = null;
}

window.onload = fetchPelanggan();